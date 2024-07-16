function getData() {
    return JSON.parse($.ajax({
        type: 'GET',
        url: '/eo/last-seven',
        dataType: 'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}

function getParticipant() {
    return JSON.parse($.ajax({
        type: 'GET',
        url: '/eo/count-month',
        dataType: 'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}

var participant = getParticipant();

var data = getData();

$(function () {
    'use strict';

    var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
    }

    var mode = 'index';
    var intersect = true;

    var $salesChart = $('#sales-chart');
    // eslint-disable-next-line no-unused-vars
    var salesChart = new Chart($salesChart, {
        type: 'bar',
        data: {
            labels: data.month,
            datasets: [
                {
                    backgroundColor: '#6F42C1',
                    borderColor: '#6F42C1',
                    data: data.count
                },
                {
                    backgroundColor: '#FD7E14',
                    borderColor: '#FD7E14',
                    data: participant
                }
            ]
        },
        options: {
            maintainAspectRatio: false,
            tooltips: {
                mode: mode,
                intersect: intersect
            },
            hover: {
                mode: mode,
                intersect: intersect
            },
            legend: {
                display: false
            },
            scales: {
                yAxes: [{
                        // display: false,
                        gridLines: {
                            display: true,
                            lineWidth: '4px',
                            color: 'rgba(0, 0, 0, .2)',
                            zeroLineColor: 'transparent'
                        },
                        ticks: $.extend({
                            beginAtZero: true,

                            // Include a dollar sign in the ticks
                            callback: function (value) {
                                if (value >= 1000) {
                                    value /= 1000;
                                }
                                return +value;
                            }
                        }, ticksStyle)
                    }],
                xAxes: [{
                        display: true,
                        gridLines: {
                            display: false
                        },
                        ticks: ticksStyle
                    }]
            }
        }
    })

})