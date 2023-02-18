import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_bloc.dart';
import 'package:news_analysis_design/bloc/news_state.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/container_most_seen.dart';

import '../../../../data/models/api_result_model.dart';
import '../../../../rss/g2j.dart';

class BuildMostSeenContainer extends StatelessWidget {
  const BuildMostSeenContainer({Key? key}) : super(key: key);

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
            return buildContainer(context, state.newsModel);
          } else {
            return Container();
          }
        },
      ),
    );
  }

  Widget _buildLoading() => Center(child: CircularProgressIndicator());
  Widget buildContainer(BuildContext context, NewsModel newsModel) {
    return SizedBox(
      height: 500,
      child: ListView.builder(
        itemCount: 10,
        scrollDirection: Axis.vertical,
        itemBuilder: ((context, index) {
          var date =
              G2j(index, newsModel.items![index].pubDate.toString()).g2jDate();
          var time =
              G2j(index, newsModel.items![index].pubDate.toString()).g2jTime();
          return  Padding(
            padding:const EdgeInsets.symmetric(horizontal: 40, vertical: 8),
            child: MostSeenContainer(
              author: newsModel.items![index].description.toString(),
              title: newsModel.items![index].title.toString(),
              date: date,
            ),
          );
        }),
      ),
    );
  }
}
