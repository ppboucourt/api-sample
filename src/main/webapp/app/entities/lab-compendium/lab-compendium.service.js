(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('LabCompendium', LabCompendium);

    LabCompendium.$inject = ['$resource'];

    function LabCompendium ($resource) {
        var resourceUrl =  'api/lab-compendiums/:id';

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
