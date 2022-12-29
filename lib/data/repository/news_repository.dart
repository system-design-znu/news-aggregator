import 'package:dio/dio.dart';

import '../models/api_result_model.dart';

class ApiProvider {
  final Dio _dio = Dio();
  final String _url =
      'https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss';

  Future<NewsModel> fetchNewsList() async {
    try {
      Response response = await _dio.get(_url);
      return NewsModel.fromJson(response.data);
    } catch (error, stacktrace) {
      print("Exception occured: $error stackTrace: $stacktrace");
      return NewsModel.withError("Data not found / Connection issue");
    }
  }
}
