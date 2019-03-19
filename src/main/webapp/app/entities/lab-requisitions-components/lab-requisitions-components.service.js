(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('LabRequisitionsComponents', LabRequisitionsComponents);

    LabRequisitionsComponents.$inject = ['$resource'];

    function LabRequisitionsComponents ($resource) {
        var resourceUrl =  'api/lab-requisitions-components/:id';

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
