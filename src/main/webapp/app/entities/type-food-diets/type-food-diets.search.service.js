(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeFoodDietsSearch', TypeFoodDietsSearch);

    TypeFoodDietsSearch.$inject = ['$resource'];

    function TypeFoodDietsSearch($resource) {
        var resourceUrl =  'api/_search/type-food-diets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
