import '../data/models/newsArticle.dart';
import '../data/repository/news_repository.dart';

class ApiRepository {
  final _provider = ApiProvider();
  Future<List<NewsArticle>> fetchNewsList() {
    return _provider.fetchNewsList();
  }
}

class NetworkError extends Error {}
