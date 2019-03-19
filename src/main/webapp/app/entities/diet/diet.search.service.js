(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('DietSearch', DietSearch);

    DietSearch.$inject = ['$resource'];

    function DietSearch($resource) {
        var resourceUrl =  'api/_search/diets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
