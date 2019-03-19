(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Employee', Employee);

    Employee.$inject = ['$resource'];

    function Employee ($resource) {
        var resourceUrl =  'api/employees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'employeeAccount': {url: 'api/employeeAccount', method: 'GET'},
            'therapists': {url: 'api/therapists/:teid', method: 'GET', isArray: true},
            'update': { method:'PUT' },
            'employeeIsValidPin': {
                url: 'api/employeeIsValidPin/:id/:pin', method: 'GET'
            },
            'employeesCorpPhysician': {url: 'api/employees/physicians', method: 'GET', isArray: true}

        });
    }
})();
