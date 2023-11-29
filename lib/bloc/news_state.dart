import 'package:equatable/equatable.dart';

import '../data/models/newsArticle.dart';


abstract class NewsState extends Equatable {
  const NewsState();

  @override
  List<Object?> get props => [];
}

class NewsInitial extends NewsState {}

class NewsLoading extends NewsState {}

class NewsLoaded extends NewsState {
  final List<NewsArticle> newsModel;
  const NewsLoaded(this.newsModel);
}

class NewsError extends NewsState {
  final String? message;
  const NewsError(this.message);
}