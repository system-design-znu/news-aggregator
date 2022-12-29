import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:news_analysis_design/bloc/news_event.dart';
import 'package:news_analysis_design/bloc/news_state.dart';
import 'package:news_analysis_design/rss/string.dart';

class NewsBloc extends Bloc<NewsEvent, NewsState> {
  NewsBloc() : super(NewsInitial()) {
    final ApiRepository _apiRepository = ApiRepository();

    on<GetNewsList>((event, emit) async {
      try {
        emit(NewsLoading());
        final mList = await _apiRepository.fetchNewsList();
        emit(NewsLoaded(mList));
        // if (mList.error != null) {
        //   emit(NewsError(mList.error));
        // }
      } on NetworkError {
        emit(NewsError("Failed to fetch data. is your device online?"));
      }
    });
  }
}
