import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_bloc.dart';
import 'package:news_analysis_design/bloc/news_state.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/container_most_seen.dart';

import '../../../../data/models/newsArticle.dart';
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
  Widget buildContainer(BuildContext context, List<NewsArticle> newsModel) {
    return SizedBox(
      height: 500,
      child: ListView.builder(
        scrollDirection: Axis.vertical,
        itemCount: newsModel.length,
        itemBuilder: ((context, index) {
          var date =
              G2j(index, newsModel[index].publishDate!.toString()).g2jDate();
          var time =
              G2j(index, newsModel[index].lastUpdateDate.toString()).g2jTime();
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 8),
            child: MostSeenContainer(
              author: newsModel[index].title.toString(),
              title: newsModel[index].agency.toString(),
              date: date,
            ),
          );
        }),
      ),
    );
  }
}
