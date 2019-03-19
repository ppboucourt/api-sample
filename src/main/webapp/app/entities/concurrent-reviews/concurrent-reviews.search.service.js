(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ConcurrentReviewsSearch', ConcurrentReviewsSearch);

    ConcurrentReviewsSearch.$inject = ['$resource'];

    function ConcurrentReviewsSearch($resource) {
        var resourceUrl =  'api/_search/concurrent-reviews/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
