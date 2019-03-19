(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('CountryStateSearch', CountryStateSearch);

    CountryStateSearch.$inject = ['$resource'];

    function CountryStateSearch($resource) {
        var resourceUrl =  'api/_search/country-states/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
