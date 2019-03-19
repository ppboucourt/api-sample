(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EmployeeSign', EmployeeSign);

    EmployeeSign.$inject = ['$resource'];

    function EmployeeSign ($resource) {
        var resourceUrl =  'api/employeeByLogin/:login';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false},
            'update': { method:'PUT' }
        });
    }
})();
