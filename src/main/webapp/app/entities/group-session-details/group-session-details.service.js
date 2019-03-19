(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('GroupSessionDetails', GroupSessionDetails);

    GroupSessionDetails.$inject = ['$resource', 'DateUtils'];

    function GroupSessionDetails ($resource, DateUtils) {
        var resourceUrl =  'api/group-session-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.finalized = DateUtils.convertDateTimeFromServer(data.finalized);
                        data.reviewDate = DateUtils.convertDateTimeFromServer(data.reviewDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'searchByDate': {url: 'api/group-session-details-by-date/:id/:date', method: 'GET', isArray: true},
            'assignChartsToGroupSessionDetails': {url: 'api/assign-charts-to-group-session-details', method: 'POST'},
            'groupSessionDetailsListChartsVO': {url: 'api/group-session-details-list-charts-vo/:id', method: 'GET', isArray: true}
        });
    }
})();
