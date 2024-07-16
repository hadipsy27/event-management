$(document).ready(function () {
    $('#event').DataTable({
        dom: 'Bfrtip',
        "responsive": true,
        "lengthChange": true,
        "autoWidth": false,
        buttons: [
            {
                extend: 'pdfHtml5',
                download: 'open',
                exportOptions: {
                     columns: [0, 1, 2, 3, 4 ]
                },
            }
        ]
    });
});

$(document).ready(function () {
    $('#event').DataTable();
});

(function () {
    'use strict';
    window.addEventListener('load', function () {
        var forms = document.getElementsByClassName('needs-validation');
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();