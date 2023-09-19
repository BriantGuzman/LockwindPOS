<!DOCTYPE html>
<html>
<head>
  <title>Investing Tutorial</title>
  <style>
    /* Add your custom styles here */
  </style>
</head>
<body>
  <header>
    <h1>Investing Tutorial</h1>
    <nav>
      <ul>
        <li><a href="#">Home</a></li>
        <li><a href="#">Tutorials</a></li>
        <li><a href="#">Articles</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <section>
      <h2>Welcome to Investing Tutorial</h2>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec gravida ante non ultrices malesuada. In ac lacinia ligula, a porta tortor. Mauris id tristique est, nec volutpat ex.</p>
    </section>

    <section>
      <h2>Latest Tutorials</h2>
      <ul id="tutorial-list">
        <!-- JavaScript will populate this list dynamically -->
      </ul>
    </section>
  </main>

  <footer>
    <p>&copy; 2023 Investing Tutorial. All rights reserved.</p>
  </footer>

  <script>
    // Add your JavaScript code here
    document.addEventListener('DOMContentLoaded', function() {
      // Example code to dynamically populate the tutorial list
      var tutorialList = document.getElementById('tutorial-list');
      var tutorials = [
        { title: 'Introduction to Stocks', url: 'tutorial1.html' },
        { title: 'Building a Diversified Portfolio', url: 'tutorial2.html' },
        { title: 'Understanding Mutual Funds', url: 'tutorial3.html' }
      ];

      tutorials.forEach(function(tutorial) {
        var li = document.createElement('li');
        var a = document.createElement('a');
        a.href = tutorial.url;
        a.textContent = tutorial.title;
        li.appendChild(a);
        tutorialList.appendChild(li);
      });
    });
  </script>
</body>
</html>
