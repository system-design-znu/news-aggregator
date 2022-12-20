import 'package:flutter/material.dart';

import 'container_hot_news.dart';

class BuildHotNewsContainer extends StatelessWidget {
  const BuildHotNewsContainer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(right: 24, bottom: 20),
      child: SizedBox(
        height: 326,
        child: ListView.builder(
          scrollDirection: Axis.horizontal,
          itemCount: 10,
          itemBuilder: ((context, index) {
            return const Padding(
              padding: EdgeInsets.only(left: 13),
              child: HotNewsContainer(),
            );
          }),
        ),
      ),
    );
  }
}
