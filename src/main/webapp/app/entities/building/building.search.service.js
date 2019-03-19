(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('BuildingSearch', BuildingSearch);

    BuildingSearch.$inject = ['$resource'];

    function BuildingSearch($resource) {
        var resourceUrl =  'api/_search/buildings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
