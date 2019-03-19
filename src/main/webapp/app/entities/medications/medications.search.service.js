(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('MedicationsSearch', MedicationsSearch);

    MedicationsSearch.$inject = ['$resource'];

    function MedicationsSearch($resource) {
        var resourceUrl =  'api/_search/medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
