import 'package:dio/dio.dart';

import '../models/newsArticle.dart';

class ApiProvider {
  final Dio _dio = Dio();
  final String _url = 'http://156.253.5.226:2648/top/6';

  Future<List<NewsArticle>> fetchNewsList() async {
    try {
      Response response = await _dio.get(_url,
          options: Options(headers: {
            'Accept': 'application/json',
          }));
      List<dynamic> dataList = response.data as List<dynamic>;
      List<NewsArticle> newsArticles = dataList
          .map((item) => NewsArticle.fromJson(item as Map<String, dynamic>))
          .toList();
      return newsArticles;
    } catch (error, stacktrace) {
      print("Exception occured: $error stackTrace: $stacktrace");
      throw Exception();
    }
  }
}
