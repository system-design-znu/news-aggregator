import '../data/models/api_result_model.dart';
import '../data/repository/news_repository.dart';

class ApiRepository {
  final _provider = ApiProvider();
  Future<NewsModel> fetchNewsList() {
    return _provider.fetchNewsList();
  }
}

class NetworkError extends Error {}
