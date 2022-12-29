import 'package:news_analysis_design/data/models/api_result_model.dart';
import 'package:shamsi_date/shamsi_date.dart';

NewsModel? _newsModel;

class G2j {
  var dateG;
  var date;
  int? type;
  var index;

  G2j(this.index, this.dateG) {
    g2jDate();
  }
  splitFirst(type, dateG) {
    var date = dateG.toString().split(' ');
    return (date[type]);
  }

  splitDateTime(type, pattern) {
    return int.parse(splitFirst(0, dateG).toString().split(pattern)[type]);
  }

  String g2jDate() {
    var year = splitDateTime(0, '-');
    var muonth = splitDateTime(1, '-');
    var day = splitDateTime(2, '-');
    Gregorian g = Gregorian(year, muonth, day);
    Jalali g2j1 = g.toJalali();
    return ('${g2j1.year}/${g2j1.month}/${g2j1.day}');
  }

  g2jTime() {
    var time = splitFirst(1, dateG);
    return time;
  }
}
