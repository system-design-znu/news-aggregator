class NewsArticle {
  int? id;
  String? agency;
  String? url;
  String? title;
  String? description;
  String? imageUrl;
  String? publishDate;
  String? lastUpdateDate;

  NewsArticle({
    this.id,
    this.agency,
    this.url,
    this.title,
    this.description,
    this.imageUrl,
    this.publishDate,
    this.lastUpdateDate,
  });

  factory NewsArticle.fromJson(Map<String, dynamic> json) {
    return NewsArticle(
      id: json['id'],
      agency: json['agency'],
      url: json['url'],
      title: json['title'],
      description: json['description'],
      imageUrl: json['image_url'], // Modify type if the image URL might be null
      publishDate: json['publish_date'],
      lastUpdateDate: json['last_update_date'],
    );
  }
}
