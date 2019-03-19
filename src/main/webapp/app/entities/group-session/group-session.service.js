(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('GroupSession', GroupSession);

    GroupSession.$inject = ['$resource', 'DateUtils'];

    function GroupSession ($resource, DateUtils) {
        var resourceUrl =  'api/group-sessions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    }
                    return data;
                }
            },
            'byfacility': {url: 'api/group-sessions-facility/:id' ,method: 'GET', isArray: true},
            'groupsessionstoday': {url: 'api/group-sessions-today/:id', method: 'GET', isArray: true},
            'groupsessionsbyday': {url: 'api/group-sessions-day/:id/:date', method: 'GET', isArray: true},
            'update': { method:'PUT' },
            'byfacilityend': {url: 'api/group-sessions-facility-end/:id', method: 'GET', isArray: true},
        });
    }
})();
