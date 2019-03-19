(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('LabRequisition', LabRequisition);

    LabRequisition.$inject = ['$resource'];

    function LabRequisition ($resource) {
        var resourceUrl =  'api/lab-requisitions/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
