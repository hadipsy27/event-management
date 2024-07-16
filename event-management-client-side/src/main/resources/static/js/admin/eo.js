var userId;
var table;

$(document).ready(() => {
    getByRole();
});

$.fn.dataTable.ext.errMode = function (obj, param, err) {
    var tableId = ('#userTable');
    console.log('Handling DataTable issue of Table ' + tableId);
};

function getByRole() {
    const role = "event_organizer";
    table = $('#eventOrganizer').DataTable({
        dom: 'Bfrtip',
        "responsive": true,
        "lengthChange": true,
        "autoWidth": false,
        buttons: [
            {
                extend: 'pdfHtml5',
                download: 'open',
                exportOptions: {
                    columns: [1, 2, 3, 4, 5, 6]
                },
            }
        ],
        ajax: {
            url: '/admin/get-byrole/' + role,
            dataSrc: ''
        },
        order: [[1, 'asc']],
        columns: [
            {orderable: false,
                data: null},
            {data: 'name'},
            {data: 'address'},
            {data: 'email'},
            {data: 'phoneNumber'},
            {data: 'companyUniversity'},
            {data: 'jobTitleYears'},
            {orderable: false,
                render: function (data, type, row) {
                    return `<a 
                                href="/admin/update-eo/${row.id}"
                                class='btn btn-sm text-white btn-update'>
                            Update</a>
                            <button 
                                onclick='deleteUser(${row.id})' 
                                class='btn btn-sm text-white btn-delete'>
                            Delete</button>`;
                }
            }
        ]
    });
    table.on('order.dt search.dt', function () {
        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
}

const swalBootstrap = Swal.mixin({
    customClass: {
        confirmButton: 'btn btn-danger',
        cancelButton: 'btn btn-success'
    },
    buttonsStyling: false
});

function deleteUser(id) {
    swalBootstrap.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, cancel!',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `/admin/delete/${id}`,
                type: 'DELETE',
//                beforeSend: addCsrfToken(),
                contentType: 'application/json',
                success: function (res) {
                    Swal.fire({
                        title: 'Success',
                        text: 'Country Deleted!',
                        icon: 'success',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    table.ajax.reload();
                }
            });
        }
    });
}