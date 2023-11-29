import 'package:flutter/material.dart';
import 'package:news_analysis_design/data/models/newsArticle.dart';

class HotNewsContainer extends StatelessWidget {
  HotNewsContainer(
      {Key? key,
      required this.title,
      required this.author,
      required this.describe})
      : super(key: key);
  String title;

  String author;
  String describe;

  get kTransparentImage => null;

  @override
  Widget build(BuildContext context) {
    return newMethod(NewsArticle());
  }

  Container newMethod(NewsArticle newsModel) {
    return Container(
      height: 326,
      width: 250,
      decoration: const BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(
          Radius.circular(8),
        ),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          SizedBox(
            width: 232,
            height: 232,
            child: ClipRRect(
              borderRadius: const BorderRadius.all(
                Radius.circular(15),
              ),
              child: Image.asset(
                'assets/images/image 4.png',
                fit: BoxFit.cover,
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 12, left: 4, right: 7),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Directionality(
                  textDirection: TextDirection.rtl,
                  child: Text(
                    title,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                      fontSize: 10,
                      fontFamily: 'IS',
                      fontWeight: FontWeight.w700,
                    ),
                    softWrap: true,
                    maxLines: 1,
                  ),
                ),
                const SizedBox(
                  height: 3,
                ),
                Directionality(
                  textDirection: TextDirection.rtl,
                  child: Padding(
                    padding:
                        const EdgeInsets.symmetric(horizontal: 2, vertical: 12),
                    child: Text(
                      describe,
                      style: const TextStyle(
                        fontSize: 10,
                        fontFamily: 'IS',
                        fontWeight: FontWeight.w400,
                      ),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                ),
                // Padding(
                //   padding: const EdgeInsets.only(bottom: 0, top: 2),
                //   child: Text(
                //     date,
                //     style: const TextStyle(
                //       fontSize: 10,
                //       fontFamily: 'IS',
                //       fontWeight: FontWeight.w200,
                //     ),
                //     maxLines: 1,
                //   ),
                // )
              ],
            ),
          )
        ],
      ),
    );
  }
}
