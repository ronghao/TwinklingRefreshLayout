## 存在的问题
1. 三星、酷派手机的兼容问题
2. 依赖太旧的问题->选择去除依赖还是更新依赖  done 以去除依赖
3. 自动刷新动画生硬  todo 问题待验证
4. 加载更多闪烁问题 done 做了优化
5. layout_behavior支持问题
6. 是否要支持ViewPager回弹问题
7. 是否要支持所有的View
8. 测试事件监听冲突问题
9. 内存溢出问题
10. 仿QQ视差效果

## 新发现的问题
1. beizierlayout主动调用刷新时会一片白
2. BallPulseView引入了内存泄漏
3. 新的方案，怎么让scroll更平滑；计算Footer降低与TargetView显示距离是否一致：结论，一致，问题在每次滚动的距离上
4. requestLayout时提示 **improperly called by android.support.v7.widget.AppCompatTextView**
