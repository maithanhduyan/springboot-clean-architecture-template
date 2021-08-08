/*!
 *  Active Navigation Menu
 */
("use strict");
let url = window.location.href;
var home = document.body.querySelector(
  "a[href='/blog/index.html'][class='nav-link']"
);
var about = document.body.querySelector(
  "a[href='/blog/about.html'][class='nav-link']"
);
var contact = document.body.querySelector(
  "a[href='/blog/contact.html'][class='nav-link']"
);
var blog = document.body.querySelector(
  "a[href='/blog/blog.html'][class='nav-link']"
);

if (url.includes("/blog/index.html")) {
  home.setAttribute("class", "nav-link active");
} else if (url.includes("/blog/about.html")) {
  about.setAttribute("class", "nav-link active");
} else if (url.includes("/blog/contact.html")) {
  contact.setAttribute("class", "nav-link active");
} else if (url.includes("/blog/blog.html")) {
  blog.setAttribute("class", "nav-link active");
}
