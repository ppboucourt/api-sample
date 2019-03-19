(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeDischargeTypeSearch', TypeDischargeTypeSearch);

    TypeDischargeTypeSearch.$inject = ['$resource'];

    function TypeDischargeTypeSearch($resource) {
        var resourceUrl =  'api/_search/type-discharge-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
