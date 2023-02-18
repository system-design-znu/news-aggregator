import 'package:flutter/material.dart';

class NiewNews extends StatelessWidget {
  const NiewNews({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: NestedScrollView(
        headerSliverBuilder: (context, isScrelled) {
          return [
            SliverAppBar(
              floating: true,
              pinned: false,
              toolbarHeight: 20,
              actions: [
                Row(
                  mainAxisSize: MainAxisSize.max,
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Image.asset('assets/images/short-Menu.png'),
                    SizedBox(
                      width: 27,
                    ),
                    Image.asset('assets/images/frame.png'),
                    SizedBox(
                      width: 313,
                    ),
                    GestureDetector(
                      onTap: () {
                        Navigator.pop(context);
                      },
                      child: Image.asset('assets/images/arrow-right.png'),
                    ),
                    SizedBox(
                      width: 26,
                    )
                  ],
                )
              ],
              bottom: PreferredSize(
                preferredSize: Size.fromHeight(10),
                child: Stack(
                  children: [
                    Container(
                      height: 30,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(20),
                          topRight: Radius.circular(20),
                        ),
                      ),
                    ),
                    Positioned(
                      right: 180,
                      top: 4,
                      left: 180,
                      child: Container(
                        height: 4,
                        decoration: BoxDecoration(
                          color: Color.fromARGB(26, 7, 7, 7),
                          borderRadius: BorderRadius.all(
                            Radius.circular(10),
                          ),
                        ),
                        width: 40,
                      ),
                    )
                  ],
                ),
              ),
              backgroundColor: Colors.white,
              expandedHeight: 400,
              flexibleSpace: FlexibleSpaceBar(
                background: Image.asset(
                  'assets/images/image-news-post.png',
                  fit: BoxFit.cover,
                ),
              ),
            ),
          ];
        },
        body: getContainer(),
      ),
    );
  }

  Widget getContainer() {
    return SingleChildScrollView(
      child: Column(
        children: [
          topSub(),
          SizedBox(
            height: 30,
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.end,
            children: [
              Text(
                '''
پاسـخ مـنـفی پــورتـو به چـلـسی بـرای جــذب طـارمـی
با طعم تهدید!
''',
                style: TextStyle(
                  fontSize: 20,
                  fontFamily: 'SM',
                  color: Colors.black,
                ),
                textDirection: TextDirection.rtl,
              )
            ],
          ),
          tagContainer(),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 24, vertical: 10),
            child: Text(
              '''
باشگاه چلسی که پیگیر جذب مهدی طارمی مهاجـم ایران بـود، با پاسـخ
منفی باشگاه پورتو مواجه شد و این بازیـکن باوجود رویای بازی در لیگ
برتر انگلیس فعلا در پرتغال ماندنی است.
''',
              style: TextStyle(
                fontSize: 16,
                fontFamily: 'SM',
                color: Colors.black,
              ),
              textDirection: TextDirection.rtl,
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 24, vertical: 0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Padding(
                  padding: EdgeInsets.only(right: 15),
                  child: Text(
                    '''
بحث پیشنهاد باشگاه چلسـی انـگـلیس به پـورتـو بـرای جـذب مـهدی
طــارمـی در آخـریــن ســاعــات نــقـل و انـتـقـالـات فــوتـبـال اروپـا یــکـی از
سوژه‌های اصلی هواداران دو تیم بود. این خبر در حالی رسانه‌ای شد
که گفته می‌شد چلسی برای جذب طارمی مبلغ ۲۵ میلیون یورو را به
پورتو پیشنهاد داده است.
روزنـامه «ابولا» پرتغال هم روز چهارشنـبـه ایــن خـبر را اعلـام کرد و به
دنبال آن بعضی از مطبوعات انگلیس و کشورهای دیگر هم پیشنهاد
چلسی به طارمی را دنبال کردند.
طـارمـی در لـیـگ قـهـرمـانـان دو فــصـل پــیــش اروپـا و در دیـدار مـقـابـل
چلسی عملکرد بی نظیری داشت و با یک گل قیچی برگردان، پورتو را
به پیروزی رساند. هرچند که نماینده پرتـغال به خاطر مجموع نـتـایـج
بازی رفت و برگشت حـذف شد. با ایـن حـال گـل طـارمـی حتـی تـا یک
قـدمی انـتخـاب بهـترین گـل سـال فیـفا و دریـافـت جـایزه «پوشکاش»
هم پیش رفت.
''',
                    style: TextStyle(
                      fontSize: 14,
                      fontFamily: 'SM',
                      color: Colors.black,
                    ),
                    textDirection: TextDirection.rtl,
                  ),
                ),
                Container(
                  height: 300,
                  width: 2,
                  decoration: BoxDecoration(
                    color: Color(0xffFF033E),
                  ),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  Row topSub() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: [
        Text('۵ دقیقه قبل',
            style: TextStyle(
              fontSize: 13,
              fontFamily: 'SM',
              color: Colors.black,
            )),
        Container(
          height: 26,
          width: 117,
          decoration: BoxDecoration(
            color: Color(0xffFF033E),
            borderRadius: BorderRadius.all(
              Radius.circular(13),
            ),
          ),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Text(
                'خبرگزاری آخرین خبر',
                style: TextStyle(
                  fontSize: 10,
                  fontFamily: 'SM',
                  color: Colors.white,
                ),
              ),
              Image.asset('assets/images/logo_news_name1.png')
            ],
          ),
        ),
        Row(
          children: [
            Text(
              'پیشنهاد مونیوز',
              style: TextStyle(
                fontSize: 13,
                fontFamily: 'SM',
                color: Colors.black,
              ),
            ),
            SizedBox(
              width: 5,
            ),
            Container(
              padding: EdgeInsets.only(top: 6),
              child: Image.asset('assets/images/flash-circle.png'),
            )
          ],
        )
      ],
    );
  }

  Padding tagContainer() {
    return Padding(
      padding: EdgeInsets.only(right: 24),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Container(
            height: 36,
            width: 77,
            decoration: BoxDecoration(
              color: Color(0xffFFCDD8),
              borderRadius: BorderRadius.all(Radius.circular(18)),
            ),
            child: Center(
              child: Text(
                'ورزشی',
                style: TextStyle(
                    fontFamily: 'SM', fontSize: 15, color: Color(0xffFF033E)),
              ),
            ),
          ),
          SizedBox(
            width: 15,
          ),
          Container(
              height: 36,
              width: 77,
              decoration: BoxDecoration(
                color: Color(0xffFFCDD8),
                borderRadius: BorderRadius.all(Radius.circular(18)),
              ),
              child: Center(
                child: Text(
                  'لژیونر ها',
                  style: TextStyle(
                      fontFamily: 'SM', fontSize: 15, color: Color(0xffFF033E)),
                ),
              )),
          SizedBox(
            width: 15,
          ),
          Container(
            height: 36,
            width: 77,
            decoration: BoxDecoration(
              color: Color(0xffFFCDD8),
              borderRadius: BorderRadius.all(Radius.circular(18)),
            ),
            child: Center(
              child: Text(
                'فوتبال اروپا',
                style: TextStyle(
                  fontFamily: 'SM',
                  fontSize: 15,
                  color: Color(0xffFF033E),
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
