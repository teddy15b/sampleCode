$(function() {

    var loginStatus = window.location.search;

    if (loginStatus === '?error') {
        $('#status').text('Invalid username or password!');
    }
    else if (loginStatus === '?logout') {
        $('#status').text('You have been logged out.');
    }

    $('#loginBtn').submit(function() {
        $.ajax({
            url: '/auth/login.html',
            type: 'POST',
            data: $('#loginForm').serialize()
        });
    });
});
