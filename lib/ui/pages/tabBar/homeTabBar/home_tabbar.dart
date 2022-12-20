import 'package:flutter/material.dart';
import 'package:news_analysis_design/ui/pages/tabBar/hotNewsContainer/build_container_hot_news.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/build_container_most_seen.dart';

class HomeTabBarView extends StatefulWidget {
  const HomeTabBarView({Key? key}) : super(key: key);

  @override
  State<HomeTabBarView> createState() => _HomeTabBarViewState();
}

class _HomeTabBarViewState extends State<HomeTabBarView>
    with SingleTickerProviderStateMixin {
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
                const Text(
                  'اخبار داغ',
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
          child: BuildHotNewsContainer(),
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
                Spacer(),
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
          child: BuildMostSeenContainer(),
        )
      ],
    );
  }
}
