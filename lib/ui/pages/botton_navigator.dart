import 'package:flutter/material.dart';
import 'package:news_analysis_design/ui/pages/tabBar/homeTabBar/home_tabbar.dart';
import 'package:news_analysis_design/ui/pages/tabBar/mostSeenContainer/container_most_seen.dart';

class BottonNavigaor extends StatefulWidget {
  const BottonNavigaor({super.key});

  @override
  State<BottonNavigaor> createState() => _BottonNavigaorState();
}

class _BottonNavigaorState extends State<BottonNavigaor> {
  var _bottonNavigationBar = 0;
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 89,
      child: BottomNavigationBar(
        showSelectedLabels: false,
        showUnselectedLabels: false,
        currentIndex: _bottonNavigationBar,
        onTap: (index) {
          setState(() {
            _bottonNavigationBar = index;
          });
        },
        type: BottomNavigationBarType.fixed,
        backgroundColor: Colors.white,
        items: [
          BottomNavigationBarItem(
            label: '',
            icon: Image.asset('assets/images/icon_search.png'),
          ),
          BottomNavigationBarItem(
            label: '',
            icon: Image.asset('assets/images/icon_setting.png'),
          ),
          BottomNavigationBarItem(
            label: '',
            icon: Image.asset(
              'assets/images/icon_home.png',
            ),
          ),
        ],
      ),
    );
  }


}
