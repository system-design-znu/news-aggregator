import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/data/models/api_result_model.dart';
import 'package:news_analysis_design/rss/g2j.dart';

import '../../../../bloc/news_bloc.dart';
import '../../../../bloc/news_event.dart';
import '../../../../bloc/news_state.dart';
import 'container_hot_news.dart';

class BuildHotNewsContainer extends StatefulWidget {
  BuildHotNewsContainer({Key? key}) : super(key: key);

  @override
  State<BuildHotNewsContainer> createState() => _BuildHotNewsContainerState();
}

class _BuildHotNewsContainerState extends State<BuildHotNewsContainer> {
  final NewsBloc _newsBloc = NewsBloc();
  NewsModel? newsModel;
  G2j? _g2j;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _newsBloc.add(GetNewsList());
  }

  @override
  Widget build(BuildContext context) {
    return BlocListener<NewsBloc, NewsState>(
      listener: (context, state) {
        if (state is NewsError) {
          Text(state.message!);
        }
      },
      child: BlocBuilder<NewsBloc, NewsState>(
        builder: (context, state) {
          if (state is NewsInitial) {
            return _buildLoading();
          } else if (state is NewsLoading) {
            return _buildLoading();
          } else if (state is NewsLoaded) {
            return containerBuilder(context, state.newsModel);
          } else {
            return Container();
          }
        },
      ),
    );
  }

  Widget _buildLoading() => Center(child: CircularProgressIndicator());

  Padding containerBuilder(BuildContext context, NewsModel newsModel) {
    return Padding(
      padding: const EdgeInsets.only(right: 24, bottom: 20),
      child: SizedBox(
        height: 326,
        child: ListView.builder(
          reverse: true,
          itemCount: newsModel.items!.length,
          scrollDirection: Axis.horizontal,
          itemBuilder: ((context, index) {
            //G2j(index, newsModel.items![index].pubDate.toString());
            var date = G2j(index, newsModel.items![index].pubDate.toString())
                .g2jDate();
            var time = G2j(index, newsModel.items![index].pubDate.toString())
                .g2jTime();
            //var date = _g2j!.dateG(newsModel.items![index].pubDate.toString());
            //ConvertDate();

            return GestureDetector(
              child: Padding(
                padding: EdgeInsets.only(left: 13),
                child: HotNewsContainer(
                  author: newsModel.items![index].description.toString(),
                  title: newsModel.items![index].title.toString(),
                  date: date,
                ),
              ),
              onTap: () {
                print('salam');
              },
            );
          }),
        ),
      ),
    );
  }
}
