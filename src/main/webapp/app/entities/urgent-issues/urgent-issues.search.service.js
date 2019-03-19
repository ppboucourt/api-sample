(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('UrgentIssuesSearch', UrgentIssuesSearch);

    UrgentIssuesSearch.$inject = ['$resource'];

    function UrgentIssuesSearch($resource) {
        var resourceUrl =  'api/_search/urgent-issues/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
