import 'dart:convert';

NewsModel newsModelFromJson(String str) => NewsModel.fromJson(json.decode(str));

//String newsModelToJson(NewsModel data) => json.encode(data.toJson());

class NewsModel {
  NewsModel({this.status, this.items});
  String? status;
  List<Items>? items;
  NewsModel.withError(String errorMessage) {
    print('error');
  }
  // CovidModel.fromJson(Map<String, dynamic> json) {
  //   global =
  //       json['Global'] != null ? new Global.fromJson(json['Global']) : null;
  //   if (json['Countries'] != null) {
  //     countries = [];
  //     json['Countries'].forEach((v) {
  //       countries!.add(new Countries.fromJson(v));
  //     });
  //   }
  //   date = json['Date'];
  // }


  NewsModel.fromJson(Map<String, dynamic> json) {
    if (json['items'] != null) {
      items = [];
      json['items'].forEach((v) {
        items!.add(Items.fromJson(v));
      });
    }
    status = json['status'];
  }
    Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    if (this.items != null) {
      data['items'] = this.items!.map((v) => v.toJson()).toList();
    }
    data['status'] = this.status;
    return data;
  }
}

class Items {
  Items(
      {this.title, this.pubDate, this.author, this.description, this.content});
  String? title;
  String? pubDate;
  String? author;
  String? description;
  String? content;
  Items.fromJson(Map<String, dynamic> json) {
    title = json['title'];
    pubDate = json['pubDate'];
    author = json['author'];
    description = json['description'];
    content = json['content'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['title'] = title;
    data['pubDate'] = pubDate;
    data['description'] = description;
    data['content'] = content;
    return data;
  }
}
