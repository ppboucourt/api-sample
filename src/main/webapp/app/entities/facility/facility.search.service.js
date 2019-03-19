(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('FacilitySearch', FacilitySearch);

    FacilitySearch.$inject = ['$resource'];

    function FacilitySearch($resource) {
        var resourceUrl =  'api/_search/facilities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
