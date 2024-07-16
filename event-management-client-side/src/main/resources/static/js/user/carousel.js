var popular = '';

function getTopTen() {
    return JSON.parse($.ajax({
        type: 'GET',
        url: '/user/top-ten',
        dataType: 'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}

var participant = getTopTen();

for (let i = 0; i < participant.length; i++) {
    popular += `<div class="carousel-cell">
                    <div class="card col-sm-11 border-0 shadow p-3 mb-5 bg-body rounded" style="width: 25rem; margin-right: 15px;">
                        <img class="img-fluid" src="/asset/${participant[i][2]}" alt="alternative">
                            <div class="card-body">
                                <h5 class="card-title">${participant[i][1]}</h5>
                                <p class="card-text">${#temporals.format(event.eventStart, 'dd MMMM yyyy')}${participant[i][6]} <br>${participant[i][8]} <br>Kouta ${participant[i][9]}</p>
                                <a href="/user/detail-event/${participant[i][0]}" class="btn-outline-sm" style="text-decoration: none;">Detail event</a>
                            </div>
                    </div>
                </div>`;
}

$('#popularEvent').html(popular);

var event = '';

function getAllEvent() {
    return JSON.parse($.ajax({
        type: 'GET',
        url: '/user/event/get-all',
        dataType: 'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}

var allevent = getAllEvent();

for (let i = 0; i < allevent.length; i++) {
    event += `<div class="card col-sm-3 border-0 shadow p-3 mb-5 bg-body rounded" style="width: 23rem;">
                <img class="img-fluid" src="/asset/${allevent[i].eventImage}" alt="alternative">
                    <div class="card-body">
                        <h5 class="card-title">${allevent[i].name}</h5>
                        <p class="card-text">${allevent[i].eventStart} <br>${allevent[i].location} <br>Kouta ${allevent[i].capacity}</p>
                        <a href="/user/detail-event/${allevent[i].id}" class="btn-outline-sm" style="text-decoration: none;">Detail event</a>
                    </div>
              </div>`;
}

$('#allEvent').html(event);

var practical = '';

function getPractical() {
    return JSON.parse($.ajax({
        type: 'GET',
        url: '/user/topic/1',
        dataType: 'json',
        global: false,
        async: false,
        success: function (data) {
            return data;
        }
    }).responseText);
}

var practicalEvent = getPractical();

for (let i = 0; i < practicalEvent.length; i++) {
    practical += `<div class="card col-sm-3 border-0 shadow p-3 mb-5 bg-body rounded" style="width: 23rem;">
                <img class="img-fluid" src="/asset/${practicalEvent[i].eventImage}" alt="alternative">
                    <div class="card-body">
                        <h5 class="card-title">${practicalEvent[i].name}</h5>
                        <p class="card-text">${practicalEvent[i].eventStart} <br>${practicalEvent[i].location} <br>Kouta ${practicalEvent[i].capacity}</p>
                        <a href="/user/detail-event/${practicalEvent[i].id}" class="btn-outline-sm" style="text-decoration: none;">Detail event</a>
                    </div>
              </div>`;
}

$('#practicalEvent').html(practical);