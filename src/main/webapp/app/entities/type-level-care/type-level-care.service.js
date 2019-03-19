(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeLevelCare', TypeLevelCare);

    TypeLevelCare.$inject = ['$resource'];

    function TypeLevelCare ($resource) {
        var resourceUrl =  'api/type-level-cares/:id';

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
            'forms': {url: 'api/type-level-cares/forms/:facId/:lcId', method: 'GET', isArray: true},
            'byFacility': {url: 'api/type-level-cares/facility/:id', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
