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

$( "#target" ).click(function() {
  alert( "Handler for .click() called." );
});