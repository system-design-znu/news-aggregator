import 'package:flutter/material.dart';

class MostSeenContainer extends StatelessWidget {
  const MostSeenContainer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return mostNewsContainer();
  }

  Widget mostNewsContainer() {
    return Container(
      width: 341,
      height: 72,
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
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: const [
                    Text(
                      'چند دقیقه قبل',
                      style: TextStyle(
                          fontSize: 8,
                          fontWeight: FontWeight.w500,
                          fontFamily: 'IS'),
                    ),
                    SizedBox(
                      width: 17,
                    ),
                    Text(
                      'قیمت آیفون ۱۳ به ۶۰,۰۰۰ میلیون رسید',
                      style: TextStyle(
                          fontSize: 10,
                          fontWeight: FontWeight.w700,
                          fontFamily: 'IS'),
                    ),
                  ],
                ),
                const Padding(
                  padding: EdgeInsets.symmetric(vertical: 8),
                  child: Text(
                    '!دلار ۴۰ هزار تومانی',
                    style: TextStyle(
                      fontSize: 10,
                      fontWeight: FontWeight.w500,
                      fontFamily: 'IS',
                    ),
                  ),
                ),
                Expanded(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    mainAxisSize: MainAxisSize.max,
                    children: [
                      Image.asset('assets/images/icon_like_gray.png'),
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
          Image.asset('assets/images/iphone_news.png'),
        ],
      ),
    );
  }
}
