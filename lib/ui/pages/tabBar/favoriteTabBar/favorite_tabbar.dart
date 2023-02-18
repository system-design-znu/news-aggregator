import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_bloc.dart';
import 'package:news_analysis_design/ui/pages/tabBar/hotNewsContainer/build_container_hot_news.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/build_container_most_seen.dart';
import '../../../../bloc/news_event.dart';

class FavoriteTabBarView extends StatefulWidget {
  const FavoriteTabBarView({Key? key}) : super(key: key);

  @override
  State<FavoriteTabBarView> createState() => _FavoriteTabBarViewState();
}

class _FavoriteTabBarViewState extends State<FavoriteTabBarView>
    with SingleTickerProviderStateMixin {
  final NewsBloc _newsBloc = NewsBloc();
  @override
  void initState() {
    super.initState();

    _newsBloc.add(GetNewsList());
  }

  @override
  Widget build(BuildContext context) {
    return sliverHomeTabBar();
  }

  CustomScrollView sliverHomeTabBar() {
    return CustomScrollView(
      slivers: [
        SliverToBoxAdapter(
          child: Padding(
            padding:
                const EdgeInsets.only(top: 20, bottom: 20, right: 28, left: 28),
            child: Row(
              children: [
                Row(
                  children: [
                    Image.asset('assets/images/arrow_more.png'),
                    const SizedBox(
                      width: 4,
                    ),
                    const Text(
                      'مشاهده بیشتر',
                      style: TextStyle(
                        fontFamily: 'IS',
                        fontSize: 10,
                        color: Color(0xff5474FF),
                      ),
                    ),
                  ],
                ),
                const Spacer(),
                Row(
                  children: const [
                    //Image.asset('assets/images/fire.png'),
                    SizedBox(
                      width: 8,
                    ),
                    Text(
                      'اخبار مورد علاقه',
                      style: TextStyle(
                          fontFamily: 'IS',
                          fontSize: 20,
                          fontWeight: FontWeight.w900),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
        SliverToBoxAdapter(
          child: BlocProvider(
            create: (context) => _newsBloc,
            child: BuildHotNewsContainer(),
          ),
        ),
        SliverToBoxAdapter(
          child: Padding(
            padding:
                const EdgeInsets.only(top: 20, bottom: 14, right: 28, left: 28),
            child: Row(
              children: [
                Row(
                  children: [
                    Image.asset('assets/images/arrow_more.png'),
                    const SizedBox(
                      width: 4,
                    ),
                    const Text(
                      'مشاهده بیشتر',
                      style: TextStyle(
                        fontFamily: 'IS',
                        fontSize: 10,
                        color: Color(0xff5474FF),
                      ),
                    ),
                  ],
                ),
                const Spacer(),
                const Text(
                  'اخبار پربازدید',
                  style: TextStyle(
                      fontFamily: 'IS',
                      fontSize: 20,
                      fontWeight: FontWeight.w900),
                ),
              ],
            ),
          ),
        ),
         SliverToBoxAdapter(
          child: BlocProvider(
            create: (context) => _newsBloc,
            child: BuildMostSeenContainer(),
          ),
        )
      ],
    );
  }

  Widget _buildLoading() => Center(child: CircularProgressIndicator());
}
