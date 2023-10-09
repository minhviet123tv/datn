<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<br/>
	<div class="footer">
		<div class="card footercard">
			<div style="margin: auto; text-align: left;">
				<p><img width="20px" src="Media/icon footer/user-avatar.png"> Cao Văn Việt</p>
				<p><img width="20px" src="Media/icon footer/icons8-mail-100.png"> minhviet.dragon@gmail.com</p>
				<p><img width="20px" src="Media/icon footer/icons8-phone-100.png"> 0961 120 861</p>
			</div>
		</div>
		<div class="card footercard">
			<div style="margin: auto; text-align: left;">
				<p><img width="20px" src="Media/icon footer/icons8-copyright-100.png"> Funix Education</p> 
				<p><img width="20px" src="Media/icon footer/icons8-google-maps-old-100.png"> 17 Duy Tân, Q.Cầu Giấy, Hà Nội</p>
				<p><img width="20px" src="Media/icon footer/icons8-internet-100.png"> <a href="https://funix.edu.vn/" target="_blank"> funix.edu.vn</a></p>
			</div>
		</div>
	</div>

</body>

<script>

/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}


</script>

<script>
/* MODEL BOX THANH TOÁN */
// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>
</html>