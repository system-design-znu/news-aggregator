import 'package:equatable/equatable.dart';

import '../data/models/api_result_model.dart';


abstract class NewsState extends Equatable {
  const NewsState();

  @override
  List<Object?> get props => [];
}

class NewsInitial extends NewsState {}

class NewsLoading extends NewsState {}

class NewsLoaded extends NewsState {
  final NewsModel newsModel;
  const NewsLoaded(this.newsModel);
}

class NewsError extends NewsState {
  final String? message;
  const NewsError(this.message);
}