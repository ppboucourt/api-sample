(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeFoodDiets', TypeFoodDiets);

    TypeFoodDiets.$inject = ['$resource'];

    function TypeFoodDiets ($resource) {
        var resourceUrl =  'api/type-food-diets/:id';

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
