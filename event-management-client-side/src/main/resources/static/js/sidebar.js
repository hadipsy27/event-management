let count = 0;
$('#pushmenu').on('click', function () {
    if (count === 0) {
        count += 1;
        $('#brand').attr('src', '/asset/EMS_Small.png');
        $('#brand').css('height', 35);
    } else {
        count -= 1;
        $('#brand').attr('src', '/asset/EMS_Big.png');
        $('#brand').css('height', 75);
    }
});