

# BootStrap

Bootstrap 是一个流行的**开源前端框架**，由 Twitter 团队开发并维护，旨在帮助开发者快速构建**响应式**（Responsive）和**移动优先**（Mobile-First）的网页界面。它提供了丰富的预定义 CSS 样式、JavaScript 组件和实用工具类，让开发者无需从头编写大量代码即可实现美观且兼容多设备的网页设计。



## 代码演示

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
       <!-- 让页面宽度与设备屏幕宽度一致，初始缩放比例为 1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    	<!-- CSS应该放在head中，以便浏览器尽早加载样式，避免页面渲染时的样式闪烁（FOUC） -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  </head>
  <body>
      <div class="container py-4 px-3 mx-auto">
          <button class="btn btn-primary">Bootstrap 定义的 primary 按钮</button>
      </div>
       <!-- JS通常放在body末尾，这样不会阻塞HTML内容的解析和渲染，特别是当脚本较大时，放在底部可以提高页面加载速度。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
</html>


由于Bootstrap也有操作页面元素的功能，因此 **Bootstrap JavaScript 与 React、Vue 和 Angular 等 JavaScript 框架并不完全兼容**，需要引入特定于框架的包以避免冲突

### **Bootstrap 的核心特性**

1. **响应式网格系统**
   Bootstrap 的网格系统基于 12 列的布局结构，通过 `container`、`row` 和 `col` 类实现灵活的页面布局。通过不同屏幕尺寸的断点（如 `xs`, `sm`, `md`, `lg`, `xl`），可以轻松实现内容在不同设备上的自动适配。

   ```html
   <div class="container">
     <div class="row">
       <div class="col-md-6 col-sm-12">内容块1（中屏占6列，小屏占12列）</div>
       <div class="col-md-6 col-sm-12">内容块2</div>
     </div>
   </div>
   ```

   

2. **丰富的预定义组件**
   Bootstrap 提供大量可复用的 UI 组件，例如：

   - **导航栏**（Navbar）
   - **按钮**（Buttons）和按钮组
   - **卡片**（Cards）、**轮播图**（Carousel）、**模态框**（Modal）
   - **表单控件**（Form inputs、下拉菜单）
   - **警告框**（Alerts）、**进度条**（Progress bars）等。

3. **实用工具类**
   提供便捷的 CSS 类，快速调整边距（`m-*`, `p-*`）、颜色（`text-primary`, `bg-success`）、显示隐藏（`d-none`, `d-block`）等样式，减少自定义 CSS 代码量。

4. **JavaScript 插件**
   Bootstrap 内置交互组件（如模态框、下拉菜单、轮播图），依赖 jQuery（v4 及之前版本）或原生 JavaScript（v5+），通过 `data-*` 属性即可触发功能，无需手动编写复杂逻辑。

5. **高度可定制**
   支持通过 Sass 变量修改主题颜色、字体、间距等，或通过覆盖默认 CSS 实现个性化设计。



参考资料

[Bootstrap · The most popular HTML, CSS, and JS library in the world.](https://getbootstrap.com/)

[図解たっぷりBootstrap入門](https://skillhub.jp/courses/168/lessons/913)

[CSSフレームワーク「Bootstrap」超入門](https://zenn.dev/yamap_web/articles/a2d4d213d4eb48)
