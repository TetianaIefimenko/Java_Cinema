</div>
</div>
</div>
<script type="text/javascript">
	/* if (${isTimerNeed!=null?isTimerNeed:false}) { */
	if (${timer!=null}) {
		var myInterval = setInterval(function getTimer() {
			$.ajax({
				url : 'cinema?action=view_timer',
				success : function(data) {
					$('#timer').html(data);
					if(data==="Time is over"){ 
						console.log("Time is over");
						clearInterval(myInterval);
						$.ajax({
							url : 'cinema?action=deleteNonPaidOrder',
							success: function() {
								window.location.reload();
							}								  
						});
					}
				} 					
			});			
		}, 1000);		
	}	
</script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
