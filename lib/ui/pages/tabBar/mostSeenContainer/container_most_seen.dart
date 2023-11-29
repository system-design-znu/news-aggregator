import 'package:flutter/material.dart';

import '../../../../data/models/newsArticle.dart';

// ignore: must_be_immutable
class MostSeenContainer extends StatelessWidget {
  MostSeenContainer(
      {Key? key, required this.title, required this.date, required this.author})
      : super(key: key);
  String title;
  String date;
  String author;
  @override
  Widget build(BuildContext context) {
    return mostNewsContainer();
  }

  Widget mostNewsContainer() {
    return Container(
      width: 341,
      height: 90,
      decoration: const BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(
          Radius.circular(8),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 6, horizontal: 12),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.end,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    Text(
                      date,
                      style: const TextStyle(
                        fontSize: 8,
                        fontWeight: FontWeight.w500,
                        fontFamily: 'IS',
                      ),
                    ),
                    SizedBox(
                      width: 17,
                    ),
                    SizedBox(
                      width: 203,
                      child: Directionality(
                        textDirection: TextDirection.rtl,
                        child: Text(
                          title,
                          softWrap: true,
                          //maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(
                            fontSize: 10,
                            fontWeight: FontWeight.w700,
                            fontFamily: 'IS',
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 8),
                  child: SizedBox(
                    width: 200,
                    child: Text(
                      author,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontSize: 10,
                        fontWeight: FontWeight.w500,
                        fontFamily: 'IS',
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    mainAxisSize: MainAxisSize.max,
                    children: [
                      //Image.asset('assets/images/icon_like_gray.png'),
                      const SizedBox(
                        width: 190,
                      ),
                      Container(
                        height: 20,
                        width: 40,
                        decoration: const BoxDecoration(
                          color: Color.fromRGBO(
                            84,
                            116,
                            255,
                            0.25,
                          ),
                          borderRadius: BorderRadius.all(
                            Radius.circular(2),
                          ),
                        ),
                        child: const Center(
                          child: Text(
                            'تکنولوژی',
                            style: TextStyle(
                              color: Color(0xff5474FF),
                              fontSize: 9,
                              fontWeight: FontWeight.w600,
                              fontFamily: 'IS',
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                )
              ],
            ),
          ),
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(6)),
            child: SizedBox(
              width: 60,
              height: 60,
              child: Image.asset(
                'assets/images/breaking_news.jpeg',
                fit: BoxFit.cover,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
