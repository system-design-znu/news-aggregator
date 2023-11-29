// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_bloc.dart';
import 'package:news_analysis_design/bloc/news_state.dart';
import 'package:news_analysis_design/data/repository/news_repository.dart';
import 'package:news_analysis_design/ui/pages/botton_navigation.dart';
import 'package:news_analysis_design/ui/pages/botton_navigator.dart';
import 'package:news_analysis_design/ui/pages/home_page.dart';
import 'package:news_analysis_design/ui/pages/login/login_screen.dart';
import 'package:news_analysis_design/ui/pages/tabBar/homeTabBar/home_tabbar.dart';
import 'package:news_analysis_design/ui/pages/tabBar/hotNewsContainer/build_container_hot_news.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/container_most_seen.dart';

void main() {
  MyWidget myWidget = MyWidget();
  runApp(myWidget);
}

class MyWidget extends StatelessWidget {
  const MyWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: MyApp(),
    );
  }
}

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
      child: Padding(
        padding: EdgeInsets.symmetric(horizontal: 50),
        child: Column(
          textDirection: TextDirection.rtl,
          mainAxisAlignment: MainAxisAlignment.start,
          mainAxisSize: MainAxisSize.max,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SizedBox(
              height: 200,
            ),
            Text(
              '!خوش اومدی ',
              style: TextStyle(fontFamily: 'IS'),
            ),
            SizedBox(
              height: 20,
            ),
            Container(
              height: 64,
              width: 342,
              decoration: BoxDecoration(
                border: Border.all(color: Colors.black26),
                color: Colors.white,
                borderRadius: BorderRadius.all(
                  Radius.circular(15),
                ),
              ),
              child: const TextField(
                showCursor: false,
                textDirection: TextDirection.rtl,
                decoration: InputDecoration(
                  disabledBorder: OutlineInputBorder(
                    borderSide: BorderSide(width: 2, color: Colors.black45),
                    borderRadius: BorderRadius.all(
                      Radius.circular(15),
                    ),
                  ),
                  border: InputBorder.none,
                  contentPadding:
                      EdgeInsets.only(top: 18, bottom: 18, right: 21),
                  enabled: true,
                  hintText: 'ایمیل',
                  hintTextDirection: TextDirection.rtl,
                  hintStyle: TextStyle(fontFamily: 'IS', color: Colors.black26),
                ),
              ),
            ),
            SizedBox(
              height: 16,
            ),
            Container(
              height: 64,
              width: 342,
              decoration: BoxDecoration(
                border: Border.all(color: Colors.black26),
                color: Colors.white,
                borderRadius: BorderRadius.all(
                  Radius.circular(15),
                ),
              ),
              child: const TextField(
                showCursor: false,
                textDirection: TextDirection.rtl,
                decoration: InputDecoration(
                  disabledBorder: OutlineInputBorder(
                    borderSide:
                        const BorderSide(width: 2, color: Colors.black12),
                    borderRadius: BorderRadius.all(
                      Radius.circular(15),
                    ),
                  ),
                  border: InputBorder.none,
                  contentPadding:
                      EdgeInsets.only(top: 18, bottom: 18, right: 21),
                  enabled: true,
                  hintText: 'رمز عبور',
                  hintTextDirection: TextDirection.rtl,
                  hintStyle: TextStyle(fontFamily: 'IS', color: Colors.black26),
                ),
              ),
            ),
            SizedBox(
              height: 32,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (context) => MyApp()));
              },
              style: ElevatedButton.styleFrom(
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(Radius.circular(8))),
                minimumSize: Size(342, 64),
                backgroundColor: Color(0xff5474FF),
              ),
              child: const Text(
                'وارد شدن',
                style: TextStyle(fontFamily: 'IS', fontSize: 18),
              ),
            ),
            SizedBox(
              height: 48,
            ),
            OutlinedButton(
                style: OutlinedButton.styleFrom(minimumSize: Size(342, 64)),
                onPressed: () {},
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset('assets/images/google 1.png'),
                    SizedBox(
                      width: 16,
                    ),
                    Text(
                      'با گوگل وارد شوید',
                      style: TextStyle(fontFamily: 'IS', color: Colors.black),
                    ),
                  ],
                ))
          ],
        ),
      ),
    ));
  }
}
