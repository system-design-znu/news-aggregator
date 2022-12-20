import 'package:flutter/material.dart';

class HotNewsContainer extends StatelessWidget {
  const HotNewsContainer({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
            child: Image.asset('assets/images/nft_image.png'),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 12, left: 4, right: 7),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Directionality(
                  textDirection: TextDirection.rtl,
                  child: Text(
                    'رکورد گران قیمت‌ترین ان‌اف‌تی دنیا شکسته شد',
                    style: TextStyle(
                      fontSize: 10,
                      fontFamily: 'IS',
                      fontWeight: FontWeight.w700,
                    ),
                    softWrap: true,
                  ),
                ),
                SizedBox(
                  height: 12,
                ),
                Text(
                  'ان‌اف‌تی dr.wong',
                  style: TextStyle(
                    fontSize: 10,
                    fontFamily: 'IS',
                    fontWeight: FontWeight.w400,
                  ),
                ),
                SizedBox(
                  height: 15,
                ),
                Padding(
                  padding: EdgeInsets.symmetric(horizontal: 5),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Image.asset('assets/images/icon_like_gray.png'),
                      Text(
                        'چند دقیقه قبل',
                        style: TextStyle(
                          fontSize: 10,
                          fontFamily: 'IS',
                          fontWeight: FontWeight.w200,
                        ),
                      ),
                    ],
                  ),
                )
              ],
            ),
          )
        ],
      ),
    );
  }
}
