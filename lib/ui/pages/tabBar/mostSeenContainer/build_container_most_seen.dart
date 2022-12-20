import 'package:flutter/material.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/container_most_seen.dart';

class BuildMostSeenContainer extends StatelessWidget {
  const BuildMostSeenContainer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return buildContainer();
  }

  Widget buildContainer() {
    return SizedBox(
      height: 500,
      child: ListView.builder(
        itemCount: 10,
        scrollDirection: Axis.vertical,
        itemBuilder: ((context, index) {
          return Padding(
            padding: EdgeInsets.symmetric(horizontal: 40, vertical: 8),
            child: MostSeenContainer(),
          );
        }),
      ),
    );
  }
}
