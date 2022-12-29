// ignore_for_file: prefer_const_constructors

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_bloc.dart';
import 'package:news_analysis_design/bloc/news_state.dart';
import 'package:news_analysis_design/data/repository/news_repository.dart';
import 'package:news_analysis_design/ui/pages/home_page.dart';
import 'package:news_analysis_design/ui/pages/tabBar/hotNewsContainer/build_container_hot_news.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: SafeArea(
        top: false,
        bottom: false,
        child: HomePage(),
      ),
    );
  }
}
